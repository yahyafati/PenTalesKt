import bs4
from dataclasses import dataclass
from datetime import datetime
from enum import Enum
from textblob import TextBlob

from app import db
from app.models import Rating


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


def evaluate_and_hide_rating(rating_id: int, threshold: float = __THRESHOLD, delete: bool = False) -> Rating:
    rating = Rating.query.get_or_404(rating_id)
    mood = get_mood(rating.review, threshold=threshold)
    if mood.status == MoodStatus.HOSTILE:
        if delete:
            db.session.delete(rating)
        else:
            rating.hidden = True
            rating.updated_at = datetime.utcnow()
        db.session.commit()
    return rating
