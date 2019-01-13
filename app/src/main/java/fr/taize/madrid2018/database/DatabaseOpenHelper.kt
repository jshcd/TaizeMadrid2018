package fr.taize.madrid2018.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DatabaseOpenHelper(private val myContext: Context) : SQLiteOpenHelper(myContext, DATABASE_NAME, null, DATABASE_VERSION) {

    //Create a empty database on the system
    @Throws(IOException::class)
    fun createDatabase() {
        val dbExist = checkDataBase()
        if (!dbExist) {
            this.readableDatabase
            try {
                this.close()
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    //Check database already exist or not
    private fun checkDataBase(): Boolean {
        var checkDB = false
        try {
            val myPath = String.format(DATABASE_PATH, Environment.getDataDirectory().absolutePath,
                    myContext.packageName, DATABASE_NAME)
            val dbfile = File(myPath)
            checkDB = dbfile.exists()
        } catch (e: SQLiteException) {
        }

        return checkDB
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    @Throws(IOException::class)
    private fun copyDataBase() {
        val mInput = myContext.getAssets().open(DATABASE_NAME)
        val outFileName = String.format(DATABASE_PATH, Environment.getDataDirectory().absolutePath,
                myContext.packageName, DATABASE_NAME)
        val mOutput = FileOutputStream(outFileName)
        val mBuffer = ByteArray(2024)
        var mLength: Int = mInput.read(mBuffer)
        while (mLength > 0) {
            mOutput.write(mBuffer, 0, mLength)
            mLength = mInput.read(mBuffer)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    //delete database
    fun deleteDatabase() {
        val file = File(String.format(DATABASE_PATH, Environment.getDataDirectory().absolutePath,
                myContext.packageName, DATABASE_NAME))
        if (file.exists()) {
            file.delete()
            println("delete database file.")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.")
            deleteDatabase()
            createDatabase()
        }
    }

    companion object {
        val DATABASE_NAME = "database.sqlite"
        val DATABASE_PATH = "%s/data/%s/databases/%s"
        val DATABASE_VERSION = 2
    }

}