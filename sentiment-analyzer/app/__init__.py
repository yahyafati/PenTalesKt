# app/__init__.py
import dotenv
import flask
import os
from flask_sqlalchemy import SQLAlchemy

# Load environment variables from .env file
dotenv.load_dotenv()
print(os.environ.get('DATABASE_URI'))
flask_app = flask.Flask(__name__)
flask_app.config['SQLALCHEMY_DATABASE_URI'] = os.environ.get('DATABASE_URI')
# flask_app.config['SECRET_KEY'] = os.environ.get('SECRET_KEY')
db = SQLAlchemy(flask_app)

from app import routes
