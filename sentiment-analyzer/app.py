import argparse
import logging
import os
from dotenv import load_dotenv

from app import flask_app

load_dotenv()

if __name__ == '__main__':
    from waitress import serve

    parser = argparse.ArgumentParser()
    parser.add_argument('--port', type=int, default=5000)
    args = parser.parse_args()
    port = os.getenv('PORT') or args.port or 5000
    debug = os.getenv('DEBUG_MODE') == '1'

    logging.info(f'Port: {port}')
    logging.info(f'Debug: {debug}')

    serve(flask_app, host='0.0.0.0', port=port, threads=4)
