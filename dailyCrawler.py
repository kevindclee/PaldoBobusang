import requests
import pandas as pd
import json
from datetime import datetime
from fake_useragent import UserAgent

today = datetime.now().strftime('%Y-%m-%d')
p_cert_key = '39ad110d-8304-44da-b584-38ec75b1f6e1'
p_cert_id = '2778'
p_startday = '2023-02-04'
p_endday = today
p_itemcategorycode = None
p_itemcode = None
p_productrankcode = '04'
p_countrycode = 1101

items = pd.read_csv('itemsHavingPrice.csv', encoding='cp949')
location = pd.read_csv('location.csv', encoding='cp949')
location.drop(161, axis=0, inplace=True)
location['COUNTRY_CODE'] = location['COUNTRY_CODE'].fillna(-1).astype('int16')
location['LOCATION_ID'] = location['LOCATION_ID'].astype('int32')
markets = location[location['COUNTRY_CODE'] != -1].loc[:, ['LOCATION_ID', 'CITY_NAME', 'COUNTRY_CODE']]

userAgent = UserAgent()
sucess = False
market_idx = 0
item_idx = 0

while not sucess:
  for i in range(len(markets)):
    locationId = markets.iloc[i, 0]
    city = markets.iloc[i, 1][:2]
    countryCode = markets.iloc[i, 2]
    for j in range(len(items)):
      data = {}
      p_itemcategorycode = items.iloc[j, 0]
      p_itemcode = items.iloc[j, 1]

      try:
        headers = {'User-Agent': userAgent.random}
        url = f'http://www.kamis.or.kr/service/price/xml.do?action=periodProductList&p_productclscode=01&p_startday={p_startday}&p_endday={p_endday}&p_itemcategorycode={p_itemcategorycode}&p_itemcode={p_itemcode}&p_productrankcode={p_productrankcode}&p_countrycode={countryCode}&p_cert_key={p_cert_key}&p_cert_id={p_cert_id}&p_returntype=json'
        req = requests.get(url, headers=headers)
        print(f'{city}: {p_itemcode}')
        
        if req.content == b'':
          continue

        res = req.json()
        
        if type(res['data']) == list:
          continue

        df = pd.DataFrame(res['data']['item'])
        df = df[df.price != '-']
        price = df[df['countyname'].str.contains(city)].iloc[0, 6]
        
        if ',' in price:
          priceValue = price.replace(',', '')
        else:
          priceValue = price
       
        data['locationId'] = str(locationId)
        data['itemCode'] = str(p_itemcode)
        data['priceDate'] = today
        data['priceValue'] = int(priceValue)
        
        headers = {'content-type': 'application/json;charset=utf-8'}
        requests.post('http://localhost:8090/prices', data=json.dumps(data), headers=headers)
      
      except:
        market_idx, item_idx = i, j
        continue

  sucess = True


