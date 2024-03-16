from flask import jsonify, request

from app import flask_app
from app import service


@flask_app.route('/evaluate-text', methods=['POST'])
def get_evaluation():
    data = request.json
    if not data:
        return jsonify({
            'error': 'request body is required'
        }), 400
    if 'text' not in data:
        return jsonify({
            'error': 'text field is required'
        }), 400
    input_text = data['text']
    threshold = request.args.get('threshold', default=0.4, type=float)
    mood = service.get_mood(input_text, threshold=threshold)

    return jsonify({
        'status': mood.status.name,
        'sentiment': mood.sentiment,
        'text': mood.text[:100]
    })
