from flask import jsonify, request

from app import flask_app
from app import service
from app.models import Rating, Comment


@flask_app.route('/evaluate-rating/<int:rating_id>', methods=['GET'])
def get_rating_evaluation(rating_id):
    rating = Rating.query.get(rating_id)
    sentiment = service.get_sentiment(rating.review)

    return jsonify({
        'id': rating.id,
        'review': rating.review,
        'sentiment': sentiment
    })


@flask_app.route('/evaluate-rating/<int:rating_id>', methods=['POST'])
def post_rating_evaluation(rating_id):
    threshold = request.args.get('threshold', default=0.4, type=float)
    delete = request.args.get('delete')
    delete_bool = True if str(delete).lower() == 'true' else False
    rating = service.evaluate_and_hide_rating(rating_id, threshold=threshold, delete=delete_bool)

    return jsonify({
        'id': rating.id,
        'review': rating.review,
        'hidden': rating.hidden
    })


@flask_app.route('/evaluate-comment/<int:comment_id>', methods=['GET'])
def get_comments_evaluation(comment_id):
    comment = Comment.query.get(comment_id)
    sentiment = service.get_sentiment(comment.comment)

    return jsonify({
        'id': comment.id,
        'comment': comment.comment,
        'sentiment': sentiment
    })


@flask_app.route('/evaluate-comment/<int:comment_id>', methods=['POST'])
def post_comments_evaluation(comment_id):
    threshold = request.args.get('threshold', default=0.4, type=float)
    delete = request.args.get('delete')
    delete_bool = True if str(delete).lower() == 'true' else False
    comment = service.evaluate_and_hide_comment(comment_id, threshold=threshold, delete=delete_bool)

    return jsonify({
        'id': comment.id,
        'comment': comment.comment,
        'hidden': comment.hidden
    })
