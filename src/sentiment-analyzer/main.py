# import libraries
# download nltk corpus (first time only)
import nltk
import pandas as pd
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize

print('Downloading NLTK corpus...')
nltk.download('all', quiet=True)
print('Download complete!')

# Load the amazon review dataset

df = pd.read_csv('https://raw.githubusercontent.com/pycaret/pycaret/master/datasets/amazon.csv')

# Print the first 5 rows of the dataframe
print(df.head())


# create preprocess_text function
def preprocess_text(text):
    # Tokenize the text

    tokens = word_tokenize(text.lower())

    # Remove stop words

    filtered_tokens = [token for token in tokens if token not in stopwords.words('english')]

    # Lemmatize the tokens

    lemmatizer = WordNetLemmatizer()

    lemmatized_tokens = [lemmatizer.lemmatize(token) for token in filtered_tokens]

    # Join the tokens back into a string

    processed_text = ' '.join(lemmatized_tokens)

    return processed_text


# apply the function df

print("Preprocessing text...")
df['reviewText'] = df['reviewText'].apply(preprocess_text)
print("Preprocessing complete!")
print(df.head())
