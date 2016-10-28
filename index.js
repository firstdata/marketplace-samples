
var request = require('request');
var crypto = require('crypto');
var http = require("https");

var username = 'xxxx-xxxx-xxx-xxx-xxxx';
var secret = 'xxx-xxx-xxx-xxx-xxxxxxxx';
var url = 'https://api.developer.firstdata.com/';

var getAuthenticationHeaders = function () {
  var date = new Date().toUTCString();
  var stringToSign = 'date: ' + date.trim();
  var encodedSignature = crypto.createHmac("sha1", secret).update(stringToSign).digest("base64");
  var hmacAuth = 'hmac username="' + username + '",algorithm="hmac-sha1",headers="date",signature="' + encodedSignature + '"';

  return {
    'date': date,
    'Authorization': hmacAuth
  }
}

// Sample GET
var options = { method: 'GET',
  url: url + 'marketplace/v1/contracts/31/agreement/',
  headers: getAuthenticationHeaders() }; 

request(options, function (error, response, body) {
  if (error) throw new Error(error);
    console.log(body);
});

// Sample POST
var options = { method: 'POST',
  url: url + 'marketplace/v1/identity/questions',
  headers: getAuthenticationHeaders(),
  body: { orderId: 242 },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});