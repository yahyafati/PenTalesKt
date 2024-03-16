from datetime import datetime

from app import db  # Import the db instance from the app package


class Rating(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow, onupdate=datetime.utcnow)
    review = db.Column(db.String(500), nullable=False)
    hidden = db.Column(db.Boolean, nullable=False, default=False)

    def get_text(self):
        return self.review


class Comment(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    updated_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow, onupdate=datetime.utcnow)
    comment = db.Column(db.String(500), nullable=False)
    hidden = db.Column(db.Boolean, nullable=False, default=False)
    rating_id = db.Column(db.Integer, db.ForeignKey('rating.id'), nullable=False)
    rating = db.relationship('Rating', backref=db.backref('comment', lazy=True))

    def get_text(self):
        return self.comment
