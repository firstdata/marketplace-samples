$key = "XXXXXXXXXXXXXXXXXXXXXXX"; // Replace with API Secret from email
$username = "XXXXXXXXXXXXXXXXXX"; // Replace with API Username from email
$url = "XXXXXXXXXXXXXXXXXX"; // Replace with URL from email

$date = gmdate("r");
$stringToSign = 'date: ' . trim($date);
echo $encodedSignature = base64_encode(hash_hmac('sha1', $stringToSign, $key, true));
$hmacAuth = 'hmac username="' . $username . '",algorithm="hmac-sha1",headers="date",signature="' . $encodedSignature . '"';
return;
$headers = array(
    'date: ' . $date,
    'Authorization: ' . $hmacAuth,
);

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url . "/marketplace/v1/contracts/31/agreement");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
$res = curl_exec($ch);
curl_close($ch);
