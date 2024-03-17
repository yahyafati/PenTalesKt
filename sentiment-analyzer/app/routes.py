import dataclasses
from flask import jsonify, request

from app import flask_app
from app import service


@dataclasses.dataclass
class EvaluationRequest:
    id: int
    text: str
    type: str

    @staticmethod
    def from_dict(data: dict):
        return EvaluationRequest(
            id=data.get('id'),
            text=data.get('text'),
            type=data.get('type'),
        )


@flask_app.route('/evaluate', methods=['POST'])
def get_evaluation():
    data: list[dict] = request.json
    if not data:
        return jsonify({
            'data': [],
        })

    threshold = request.args.get('threshold', default=0.4, type=float)
    responses = []
    for item in data:
        item = EvaluationRequest.from_dict(item)
        mood = service.get_mood(item.text, threshold=threshold)
        responses.append({
            'id': item.id,
            'type': item.type,
            'sentiment': mood.sentiment,
        })

    return jsonify({
        'data': responses
    })


@flask_app.route('/health', methods=['GET'])
def health():
    return jsonify({
        'status': 'ok'
    })
