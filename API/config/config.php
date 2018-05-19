<?php
/**
 * Created by PhpStorm.
 * User: agusprasetiyo
 * Date: 5/13/18
 * Time: 2:27 PM
 */
$hostname = "localhost";
$username = "root";
$password = "";
$dbname = "ljpjj_api";
$conn = mysqli_connect($hostname, $username, $password, $dbname);
if (!$conn) {
    die("Koneksi Gagal: " . mysqli_connect_error());
}