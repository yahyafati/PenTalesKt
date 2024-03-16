import bs4
from dataclasses import dataclass
from enum import Enum
from textblob import TextBlob


class MoodStatus(Enum):
    HOSTILE = -1
    NEUTRAL = 0
    FRIENDLY = 1


@dataclass
class Mood:
    status: MoodStatus
    sentiment: float
    text: str = None


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
        return Mood(MoodStatus.FRIENDLY, sentiment, input_text)

    elif sentiment <= hostile_threshold:
        return Mood(MoodStatus.HOSTILE, sentiment, input_text)

    else:
        return Mood(MoodStatus.NEUTRAL, sentiment, input_text)
