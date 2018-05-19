package prasetiyo.id.d4ljpjjcrud.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable{
    private int id;
    private String nama,alamat,gender;

    public Mahasiswa(int id, String nama, String alamat, String gender) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.gender = gender;
    }

    private Mahasiswa(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        alamat = in.readString();
        gender = in.readString();
    }

    public static final Creator<Mahasiswa> CREATOR = new Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel in) {
            return new Mahasiswa(in);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(alamat);
        parcel.writeString(gender);
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getGender() {
        return gender;
    }
}
