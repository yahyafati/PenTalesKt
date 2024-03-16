import bs4
from dataclasses import dataclass
from datetime import datetime
from enum import Enum
from textblob import TextBlob

from app import db
from app.models import Rating, Comment


class MoodStatus(Enum):
    HOSTILE = -1
    NEUTRAL = 0
    FRIENDLY = 1


@dataclass
class Mood:
    status: MoodStatus
    sentiment: float


__THRESHOLD = 0.4


def get_sentiment(input_text: str) -> float:
    soup = bs4.BeautifulSoup(input_text, 'html.parser')
    text_content = soup.get_text()
    return TextBlob(text_content).sentiment.polarity


def get_mood(input_text: str, *, threshold: float = __THRESHOLD) -> Mood:
    sentiment: float = get_sentiment(input_text)

    friendly_threshold: float = threshold
    hostile_threshold: float = -threshold

    if sentiment >= friendly_threshold:
        return Mood(MoodStatus.FRIENDLY, sentiment)

    elif sentiment <= hostile_threshold:
        return Mood(MoodStatus.HOSTILE, sentiment)

    else:
        return Mood(MoodStatus.NEUTRAL, sentiment)


def evaluate_and_hide(item: db.Model, item_id: int, threshold: float = __THRESHOLD, delete: bool = False) -> db.Model:
    item = item.query.get_or_404(item_id)

    if not hasattr(item, 'get_text'):
        raise ValueError('The item does not have a get_text method')

    mood = get_mood(item.get_text(), threshold=threshold)
    if mood.status == MoodStatus.HOSTILE:
        if delete:
            db.session.delete(item)
        else:
            item.hidden = True
            item.updated_at = datetime.utcnow()
        db.session.commit()
    return item


def evaluate_and_hide_rating(rating_id: int, threshold: float = __THRESHOLD, delete: bool = False) -> Rating:
    return evaluate_and_hide(Rating, rating_id, threshold=threshold, delete=delete)


def evaluate_and_hide_comment(comment_id: int, threshold: float = __THRESHOLD, delete: bool = False) -> Comment:
    return evaluate_and_hide(Comment, comment_id, threshold=threshold, delete=delete)
