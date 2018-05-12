import requests
from json import JSONDecoder

http_url = "https://api-cn.faceplusplus.com/imagepp/v1/recognizetext"
key = "deVU3Lul6eaHnocrLx8du_51CFogCOsb"
secret = "fzC0tGQa_tLwXZv2cwJTfDEMvqawsVLq"
filepath = "image/7_1.jpg"

data = {"api_key": key, "api_secret": secret}
file = {"image_file": open(filepath, "rb")}
response = requests.post(http_url, data = data, files = file)

req_con = response.content.decode('utf-8')
req_dict = JSONDecoder().decode(req_con)

print(req_dict)
for i in req_dict['result']:
	print(i['value'])
