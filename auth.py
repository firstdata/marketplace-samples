from hashlib import sha1
import email.utils
import hmac
import requests

secret = "xxxx-xxxx-xxx-xxx-xxxx" # replace with secret from email
username = "xxxx-xxxx-xxx-xxx-xxxx" # replace with username from email
url = 'xxxxxxxxxxxxxxxxxxxxxxxxxxxx' # replace with url from email

dt = email.utils.formatdate(usegmt="true")
stringToSign = 'date: ' + dt;

encodedSignature = hmac.new(secret, stringToSign, sha1).digest().encode("base64").rstrip('\n')

hmacAuth = 'hmac username="' + username + '",algorithm="hmac-sha1",headers="date",signature="' + encodedSignature + '"';

headers = {
    'date': dt,
    'Authorization': hmacAuth
}

data = {
	"companyId": 386
}

endpoint = url + "marketplace/v1/pricing/global"
r = requests.post(endpoint, json=data, headers=headers)
print(r.text)