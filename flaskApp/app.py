# https://towardsdatascience.com/create-an-api-to-deploy-machine-learning-models-using-flask-and-heroku-67a011800c50?gi=8bd80a4cd3bd
import pandas as pd
from flask import Flask, jsonify, request
import pickle

app = Flask(__name__)

model = pickle.load(open('model.pkl','rb'))

@app.route('/hello')
def hello():
    return '<h1>Hi! Welcome to SpiWorld</h1>'


@app.route('/output', methods=['POST'])
def predict():
    data = request.get_json(force=True)
    # return jsonify(data)

    crops = ['Crop_Arecanut', 'Crop_Arhar/Tur', 'Crop_Bajra', 'Crop_Banana', 'Crop_Barley', 'Crop_Beans & Mutter(Vegetable)', 'Crop_Bhindi', 'Crop_Bitter Gourd', 'Crop_Black pepper', 'Crop_Bottle Gourd', 'Crop_Brinjal', 'Crop_Cabbage', 'Crop_Cardamom', 'Crop_Cashewnut', 'Crop_Cashewnut Raw', 'Crop_Castor seed', 'Crop_Citrus Fruit', 'Crop_Coconut', 'Crop_Coffee', 'Crop_Coriander', 'Crop_Cotton(lint)', 'Crop_Cowpea(Lobia)', 'Crop_Cucumber', 'Crop_Drum Stick', 'Crop_Dry chillies', 'Crop_Dry ginger', 'Crop_Garlic', 'Crop_Ginger', 'Crop_Gram', 'Crop_Grapes', 'Crop_Groundnut', 'Crop_Guar seed', 'Crop_Horse-gram', 'Crop_Jack Fruit', 'Crop_Jowar', 'Crop_Korra', 'Crop_Lemon', 'Crop_Linseed', 'Crop_Maize', 'Crop_Mango', 'Crop_Masoor', 'Crop_Mesta', 'Crop_Moong(Green Gram)', 'Crop_Moth', 'Crop_Niger seed', 'Crop_Oilseeds total', 'Crop_Onion', 'Crop_Orange', 'Crop_Other  Rabi pulses', 'Crop_Other Cereals & Millets', 'Crop_Other Fresh Fruits', 'Crop_Other Kharif pulses', 'Crop_Other Vegetables', 'Crop_Papaya', 'Crop_Peas  (vegetable)', 'Crop_Peas & beans (Pulses)', 'Crop_Pineapple', 'Crop_Pome Fruit', 'Crop_Pome Granet', 'Crop_Potato', 'Crop_Pulses total', 'Crop_Pump Kin', 'Crop_Ragi', 'Crop_Rapeseed &Mustard', 'Crop_Rice', 'Crop_Rubber', 'Crop_Safflower', 'Crop_Samai', 'Crop_Sannhamp', 'Crop_Sapota', 'Crop_Sesamum', 'Crop_Small millets', 'Crop_Snak Guard', 'Crop_Soyabean', 'Crop_Sugarcane', 'Crop_Sunflower', 'Crop_Sweet potato', 'Crop_Tapioca', 'Crop_Tea', 'Crop_Tobacco', 'Crop_Tomato', 'Crop_Total foodgrain', 'Crop_Turmeric', 'Crop_Urad', 'Crop_Varagu', 'Crop_Wheat', 'Crop_other fibres', 'Crop_other misc. pulses', 'Crop_other oilseeds']
    input = []
    no_of_crops = len(crops)      #89
    for i in range(no_of_crops):
        input.append([0]*103)
    #set crop values
    j=8
    for i in range(no_of_crops):
        input[i][j]=1
        j+=1        

    for i in range(no_of_crops):
        if data:
            input[i][0] = data['Area'] 	#Area
            input[i][1] = data['Rainfall'] 	#Rainfall
            input[i][2] = data['Elevation'] 	#Elevation
            input[i][3] = data['Max Temperature'] 	#Max Temperature
            input[i][4] = data['Min Temperature'] 	#Min Temperature
            input[i][5] = data['Precipitation'] 	#Precipitation
            input[i][6] = data['Relative Humidity'] 	#Relative Humidity
            input[i][7] = data['Solar'] 	#Solar
            input[i][101] = 1.0 	#Season_Whole Year
        else:
            input[i][0] = request.json['Area'] 	#Area
            input[i][1] = request.form['Rainfall'] 	#Rainfall
            input[i][2] = request.form['Elevation'] 	#Elevation
            input[i][3] = request.form['Max Temperature'] 	#Max Temperature
            input[i][4] = request.form['Min Temperature'] 	#Min Temperature
            input[i][5] = request.form['Precipitation'] 	#Precipitation
            input[i][6] = request.form['Relative Humidity'] 	#Relative Humidity
            input[i][7] = request.form['Solar'] 	#Solar
            input[i][101] = 1.0 	#Season_Whole Year


    result = model.predict(input)
    output = dict()
    
    for i in range(no_of_crops):
        output[crops[i]] = result[i]

    # return output
    return jsonify({'results' : output})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port = 5000, debug=True)



