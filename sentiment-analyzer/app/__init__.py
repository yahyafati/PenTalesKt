# app/__init__.py
import flask

flask_app = flask.Flask(__name__)

from app import routes
