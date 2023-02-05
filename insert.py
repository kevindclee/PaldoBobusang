import pandas as pd
import numpy as np
import requests
import json

location = pd.read_csv("location.csv", encoding="cp949")
location.drop(161, axis=0, inplace=True)
location['COUNTRY_CODE'] = location['COUNTRY_CODE'].fillna(-1).astype('int16').astype(str).replace('-1', None)
location['LOCATION_ID'] = location['LOCATION_ID'].astype('int32').astype(str)

headers = {'content-type': 'application/json;charset=utf-8'}
for i in range(len(location)):
  data = {}
  data['locationId'] = location.iloc[i, 0]
  data['localName'] = location.iloc[i, 1]
  data['localEngName'] = location.iloc[i, 2]
  data['cityName'] = location.iloc[i, 3]
  data['cityEngName'] = location.iloc[i, 4]
  data['countryCode'] = location.iloc[i, 5]
  # requests.post('http://localhost:8090/locations', data=json.dumps(data), headers=headers)

fruit = pd.read_csv("fruit.csv", encoding="cp949")
fruit.drop(22, axis=0, inplace=True)
fruit['FRUIT_CODE'] = fruit['FRUIT_CODE'].astype('int16').astype(str)
fruit['HARVEST_START'] = fruit['HARVEST_START'].astype(str)
fruit['HARVEST_END'] = fruit['HARVEST_END'].astype(str)
fruit['ETC_DETAILS'] = fruit['ETC_DETAILS'].fillna("None")
fruit['BRIX'] = fruit['BRIX'].fillna(0).astype('int16').astype(str)

location = pd.read_csv("fruit-location.csv", encoding="cp949")
location = location.drop(319, axis=0)
location['FRUIT_CODE'] = location['FRUIT_CODE'].astype('int16').astype(str)
location['LOCATION_ID'] = location['LOCATION_ID'].astype('int32').astype(str)

headers = {'content-type': 'application/json;charset=utf-8'}
for i in range(len(fruit)):
  data = {}
  data['itemCode'] = fruit.iloc[i, 0]
  data['itemName'] = fruit.iloc[i, 1]
  data['unit'] = fruit.iloc[i, 2]
  data['itemImage'] = fruit.iloc[i, 3]
  data['harvestStart'] = fruit.iloc[i, 4]
  data['harvestEnd'] = fruit.iloc[i, 5]
  data['etcDetails'] = fruit.iloc[i, 6]
  data['brix'] = fruit.iloc[i, 7]
  data['locationIds'] = [location.iloc[x, 1] for x in location[location.FRUIT_CODE == fruit.iloc[i, 0]].index]
  print(f'{i}: {data}\n')
  # requests.post('http://localhost:8090/fruits', data=json.dumps(data), headers=headers)


vegetable = pd.read_csv("vegetable.csv", encoding="cp949")
vegetable = vegetable.drop(54, axis=0)
vegetable['VEGETABLE_CODE'] = vegetable['VEGETABLE_CODE'].astype('int16').astype(str)
vegetable['ETC_DETAILS'] = vegetable['ETC_DETAILS'].fillna("None")

location = pd.read_csv("vegetable-location.csv", encoding="cp949")
location = location.drop(318, axis=0)
location['VEGETABLE_CODE'] = location['VEGETABLE_CODE'].astype('int16').astype(str)
location['LOCATION_ID'] = location['LOCATION_ID'].astype('int32').astype(str)

headers = {'content-type': 'application/json;charset=utf-8'}
for i in range(len(vegetable)):
  data = {}
  data['itemCode'] = vegetable.iloc[i, 0]
  data['itemName'] = vegetable.iloc[i, 1]
  data['unit'] = vegetable.iloc[i, 2]
  data['itemImage'] = vegetable.iloc[i, 3]
  data['harvestStart'] = vegetable.iloc[i, 4]
  data['harvestEnd'] = vegetable.iloc[i, 5]
  data['etcDetails'] = vegetable.iloc[i, 6]
  data['locationIds'] = [location.iloc[x, 1] for x in location[location.VEGETABLE_CODE == vegetable.iloc[i, 0]].index]
  # requests.post('http://localhost:8090/vegetables', data=json.dumps(data), headers=headers)
