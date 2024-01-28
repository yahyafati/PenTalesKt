import os

import dotenv

dotenv.load_dotenv(dotenv.find_dotenv())


def get_goodreads_dataset_path():
    return os.getenv("GOODREADS_DATASET_PATH")
