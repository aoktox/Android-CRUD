<?php
/**
 * Created by PhpStorm.
 * User: agusprasetiyo
 * Date: 5/13/18
 * Time: 2:32 PM
 */
include '../model/mahasiswa.php';
include '../config/config.php';

header('Content-Type: application/json');

function isTheseParametersAvailable($params)
{
    $available = true;
    $missingparams = "";
    foreach ($params as $param) {
        if (!isset($_POST[$param]) || strlen($_POST[$param]) <= 0) {
            $available = false;
            $missingparams = $missingparams . ", " . $param;
        }
    }
    if (!$available) {
        $response = array();
        $response['error'] = true;
        $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
        echo json_encode($response);
        die();
    }
}

$response = array();
if (isset($_GET['apicall'])) {
    switch ($_GET['apicall']) {
        case 'create_mahasiswa':
            isTheseParametersAvailable(array('nama', 'alamat','gender'));
            $result = createMahasiswa($conn, $_POST['nama'], $_POST['alamat'],$_POST['gender']);
            if ($result) {
                $response['error'] = false;
                $response['message'] = 'Mahasiswa berhasil ditambahkan';
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error';
            }
            break;
        case 'update_mahasiswa':
            isTheseParametersAvailable(array('id', 'nama', 'alamat','gender'));
            $result = updateMahasiswa($conn, $_POST['id'], $_POST['nama'], $_POST['alamat'],$_POST['gender']);
            if ($result) {
                $response['error'] = false;
                $response['message'] = 'Mahasiswa berhasil diperbarui';
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error';
            }
            break;
        case 'get_mahasiswa':
            $response['error'] = false;
            $response['message'] = 'Request successfully completed';
            $response['mahasiswa'] = getMahasiswa($conn);
            break;
        case 'delete_mahasiswa':
            if (isset($_GET['id'])) {
                if (deleteMahasiswa($conn, $_GET['id'])) {
                    $response['error'] = false;
                    $response['message'] = 'Delete mahasiswa berhasil';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Some error';
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Nothing to delete';
            }
            break;
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}
echo json_encode($response);