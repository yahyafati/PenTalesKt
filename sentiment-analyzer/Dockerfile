FROM python:3.12

# Set the working directory in the container
WORKDIR /app

COPY requirements.txt /app/requirements.txt

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Copy the current directory contents into the container at /app
COPY . /app


# Make port 5000 available to the world outside this container
EXPOSE 5000

# Define environment variable
ENV DEBUG_MODE=0
ENV PORT=$FLASK_DOCKER_PORT

# Run app.py when the container launches
CMD ["python", "app.py"]

