from flask import Flask, request, json, jsonify, abort
import flask
import uuid

app = Flask(__name__)
app.config['DEBUG'] = True

barks = {}

@app.route('/barks/<bark_id>', methods = ['GET'])
def get_bark(bark_id):
	if bark_id in barks:
		return jsonify(barks[bark_id])
	else:
		abort(404)

@app.route('/barks', methods = ['POST'])
def save_bark():
	bark = request.json
	bark['id'] = str(uuid.uuid4())
	barks[bark['id']] = bark
	return jsonify(bark)
