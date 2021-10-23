<?php
header("Content-Type: application/json");
$servername = "127.0.0.1";
$username = "root";
$password = "zks350982";
$dbname = "pixiv_web_data";

$output = array('msg' => '', 'code' => 0);

function SHA256Hex($str): string
{
    $re=hash('sha256', $str, true);
    return bin2hex($re);
}

/**
 * 发送post请求
 * @param string $url 请求地址
 * @param array $post_data post键值对数据
 * @return string
 */
function send_post(string $url, array $post_data): string
{
    $postData = http_build_query($post_data);
    $options = array(
        'http' => array(
            'method' => 'POST',
            'header' => 'Content-type:application/x-www-form-urlencoded',
            'content' => $postData,
            'timeout' => 15 * 60 // 超时时间（单位:s）
        )
    );
    $context = stream_context_create($options);
    return file_get_contents($url, false, $context);
}

function randomSalt(int $pw_length): string
{
    $randomPassword = "";
    for ($i = 0; $i < $pw_length; $i++) {
        $randomPassword .= chr(mt_rand(33, 126));
    }
    return $randomPassword;
}

function createToken(string  $userName, string $userEmail): string{
    $timestamp = strval(time());
    $rawHash = str_split(SHA256Hex($userName . ":mikoto:" . $userEmail . "114514:1919810". $timestamp));

    $timestamp = str_split($timestamp);

    $finalStr = '';
    for ($i = 0; $i < count($rawHash); $i++) {
        $finalStr = $finalStr . $rawHash[$i];
        if (count($timestamp) != $i) {
            $finalStr = $finalStr . $timestamp[$i];
        }
    }
    if (count($timestamp) < 10){
        return $finalStr . "0" . strval(count($timestamp));
    } else {
        return $finalStr . strval(count($timestamp));
    }
}

$workType = $_POST["workType"];
$outputData = array('error' => false, 'msg' => '');

if ($workType == "register") {
    $server = $_POST["server"];
    $token = $_POST["token"];
    $userName = $_POST["userName"];
    $userPassword = $_POST["userPassword"];
    $userPasswordSalt = randomSalt(10);
    $userEmail = $_POST["userEmail"];
    $userIp = $_POST["userIp"];

    $post_data = array(
        'id' => '60c634906aed7207789f4ddf',
        'secretkey' => 'b62ae7ea7fe149938cb2a43c4081e309',
        'scene' => 0,
        'token' => $token,
        'ip' => $userIp
    );
    $result = json_decode(send_post($server, $post_data), true);
    if ($result["success"] == 1) {
        if ($userEmail != '' and $userName != '' and $userPassword != '' and $userPasswordSalt != '') {

            $conn = new mysqli($servername, $username, $password, $dbname);
            if ($conn->connect_error) {
                die("Connect_Failed: " . $conn->connect_error);
            }

            $sql = "INSERT INTO pixiv_data.pixiv_forward_users (user_name, user_password, user_password_salt, user_email, user_email_is_confirm, create_time, update_time)
VALUES ('$userName', '$userPassword', '$userPasswordSalt', '$userEmail', 0, '" . date('Y-m-d h:i:s', time()) . "', '". date('Y-m-d h:i:s', time()) ."')";
            if ($conn->query($sql) === TRUE) {
                $outputData['error'] = false;
                $outputData['msg'] = 'success';
            } else {
                $outputData['error'] = true;
                $outputData['msg'] = 'ERROR::Failed to insert data';
            }
            $conn->close();
        } else{
            $outputData['error'] = true;
            $outputData['msg'] = 'ERROR::Data is null';
        }
    }
} else if ($workType == 'login') {
    $userName = $_POST["userName"];
    $userPassword = base64_decode($_POST["userPassword"]);
    $userIp = $_POST["userIp"];

    if ($userName != '' and $userPassword != '' and $userIp != '') {

        $conn = new mysqli($servername, $username, $password, $dbname);
        if ($conn->connect_error) {
            die("Connect_Failed: " . $conn->connect_error);
        }

        $sql = "SELECT * from users WHERE user_name='$userName';";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                if (SHA256Hex($userPassword . $row['user_password_salt']) == $row['user_password']){
                    $outputData['error'] = false;
                    $outputData['msg'] = 'success';
                }else{
                    $outputData['error'] = true;
                    $outputData['msg'] = 'ERROR::Wrong password';
                }
            }
        } else {
            $outputData['error'] = true;
            $outputData['msg'] = 'ERROR::Cannot find user';
        }
        $conn->close();
    } else{
        $outputData['error'] = true;
        $outputData['msg'] = 'ERROR::Data is null';
    }
}

echo json_encode($outputData);