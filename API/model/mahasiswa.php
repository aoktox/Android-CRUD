<?php
/**
 * Created by PhpStorm.
 * User: agusprasetiyo
 * Date: 5/13/18
 * Time: 2:28 PM
 */

const FEMALE="P";
const MALE="L";

function createMahasiswa($conn,$nama, $alamat,$gender){
    $sql="INSERT INTO profile(nama,alamat,gender) VALUES('$nama','$alamat','$gender')";
    if(mysqli_query($conn,$sql)) {
        return true;
    }
    throw new Exception("$sql");
    mysqli_close($conn);
    return false;
}

function getMahasiswa($conn){
    $sql="SELECT * FROM profile";
    $result=mysqli_query($conn, $sql);
    $mahasiswa=array();
    while($row = mysqli_fetch_array($result)){
        $mahasiswa_temp=array();
        $mahasiswa_temp['id']=$row['id'];
        $mahasiswa_temp['nama']=$row['nama'];
        $mahasiswa_temp['alamat']=$row['alamat'];
        $mahasiswa_temp['gender']=$row['gender'];
        array_push($mahasiswa,$mahasiswa_temp);
    }
    mysqli_close($conn);
    return $mahasiswa;
}

function updateMahasiswa($conn,$id,$nama, $alamat,$gender)
{
    $sql = "UPDATE profile SET nama='$nama', alamat='$alamat',gender='$gender' WHERE id=$id";
    if(mysqli_query($conn,$sql)) {
        return true;
    }
    mysqli_close($conn);
    return false;
}
function deleteMahasiswa($conn,$id)
{
    $sql = "DELETE FROM profile WHERE id=$id";
    if(mysqli_query($conn,$sql)) {
        return true;
    }
    mysqli_close($conn);
    return false;
}