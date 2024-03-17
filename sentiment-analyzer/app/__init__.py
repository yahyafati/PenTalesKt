# app/__init__.py
import flask
import logging

flask_app = flask.Flask(__name__)
logging.basicConfig(level=logging.INFO)


@flask_app.before_request
def before_request():
    logging.info(f'Requested path: {flask.request.path} from {flask.request.remote_addr}')


@flask_app.after_request
def after_request(response):
    logging.info(f'Response status: {response.status_code}, content type: {response.content_type}')
    return response


from app import routes
