import pandas as pd
import numpy as np
import requests
import json

price = pd.read_csv("price.csv")
price['itemcode'] = price['itemcode'].astype(str)
price['locationid'] = price['locationid'].astype(str)

data = {}
headers = {'content-type': 'application/json;charset=utf-8'}
for i in range(len(price)):
  priceDate = f'{price.iloc[i, 4]}-{price.iloc[i, 5].replace("/", "-")}'

  if ',' in price.iloc[i, 6]:
    priceValue = price.iloc[i, 6].replace(',', '')
  else:
    priceValue = price.iloc[i, 6]
  
  data['itemCode'] = price.iloc[i, 1]
  data['locationId'] = price.iloc[i, 3]
  data['priceDate'] = priceDate
  data['priceValue'] = priceValue
  requests.post('http://localhost:8090/prices', data=json.dumps(data), headers=headers)
