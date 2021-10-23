<?php
function SHA256Hex($str): string
{
    $re=hash('sha256', $str, true);
    return bin2hex($re);
}

function checkNum(int $num): bool
{
    return (bool)(($num % 2));
}

function accessToken(string  $userName, string $userEmail, string $token): string{
    $timeLength = intval(substr($token,-2));

    $tokenArray = str_split(substr($token, 0, strlen($token) - 2));

    $oddToken = "";
    $evenToken = "";
    for ($i = 0; $i < count($tokenArray); $i++){
        if (checkNum($i + 1)) {
            $oddToken = $oddToken . $tokenArray[$i];
        } else {
            $evenToken = $evenToken . $tokenArray[$i];
        }
    }

    $timestamp = substr($evenToken,0 , $timeLength);

    return "1";
}

function createToken(string  $userName, string $userEmail): string{
    $timestamp = strval(time());
    $rawHash = str_split(SHA256Hex($userName . ":mikoto:" . $userEmail . "114514:1919810". $timestamp));

    $timestamp = str_split($timestamp);

    $finalStr = '';
    for ($i = 0; $i < count($rawHash); $i++) {
        $finalStr = $finalStr . $rawHash[$i] . $timestamp[$i];
    }
    if (count($timestamp) < 10){
        return $finalStr . "0" . strval(count($timestamp));
    } else {
        return $finalStr . strval(count($timestamp));
    }
}

accessToken("zklcoca", "zklcoca@163.com", "b186238282e3e9b7e0039e3614d161c8000ffa932ae4e55259d72837b644e63bf963d6149110");