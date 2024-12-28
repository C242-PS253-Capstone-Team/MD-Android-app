package com.dicoding.capstone

import FacePredictionResponse
import RetrofitClientFace
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ScanView : AppCompatActivity() {

    private lateinit var btnPick: Button
    private lateinit var btnBack: Button
    private lateinit var imageView: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDesc: TextView

    companion object {
        const val TAG = "ScanView"
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                handleImageSelection(it)
            } ?: run {
                Toast.makeText(this, "Gambar tidak dipilih.", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_view)

        // Inisialisasi view
        btnPick = findViewById(R.id.btnPick)
        imageView = findViewById(R.id.imageView)
        btnBack = findViewById(R.id.btnBack)
        tvTitle = findViewById(R.id.tvTittle)
        tvDesc = findViewById(R.id.tvDesc)

        // Set listener untuk tombol pilih gambar
        btnPick.setOnClickListener {
            pickImageGallery()
        }

        // Set listener untuk tombol kembali
        btnBack.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }

    private fun pickImageGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun handleImageSelection(uri: Uri) {
        lifecycleScope.launch {
            val file = convertUriToFile(uri)
            imageView.setImageURI(uri)
            uploadImage(file)
        }
    }

    @SuppressLint("Range")
    private fun convertUriToFile(uri: Uri): File {
        val contentResolver = contentResolver
        val fileName = contentResolver.query(uri, null, null, null, null)?.use {
            it.moveToFirst()
            it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        } ?: "temp_file.jpg"

        val tempFile = File(cacheDir, fileName)
        val inputStream = contentResolver.openInputStream(uri) ?: return tempFile
        FileOutputStream(tempFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return tempFile
    }

    private fun uploadImage(file: File) {
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("picture", file.name, requestFile)

        RetrofitClientFace.instance.detectFaceShape(body).enqueue(object :
            Callback<FacePredictionResponse> {
            override fun onResponse(call: Call<FacePredictionResponse>, response: Response<FacePredictionResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.Prediction
                    Log.d("Prediction", "Face shape: $prediction")
                    prediction?.let {
                        tvTitle.text = "Bentuk Wajah: $it"
                        tvDesc.text = getDescriptionForFaceShape(it)
                    }
                } else {
                    Log.e("Error", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<FacePredictionResponse>, t: Throwable) {
                Log.e("Error", "Network request failed: ${t.message}")
            }
        })
    }

    private fun getDescriptionForFaceShape(faceShape: String): String {
        return when (faceShape) {
            "Oblong" -> "Jika wajahmu memiliki bentuk panjang dan ramping, hindari potongan rambut yang menambah kesan panjang ke atas. Sebaiknya pilih gaya rambut yang lebih pendek untuk menciptakan kesan seimbang. Gaya dengan volume di samping atau poni dapat memberikan dimensi tambahan dan mengurangi kesan panjang pada wajah.\n \n Rekomendasi Gaya Rambut:\n- Buzz Cut\n- Crew Cut\n- Side Part\n- Fringe Up\n- Side Fringe\n- Quiff\n- Textured Crop"

            "Heart" -> "Bentuk wajahmu adalah tipe Heart, tipe ini memilki ciri dahinya yang lebar dan dagu yang runcing seperti bentuk hati. Pilih gaya rambut dengan volume lebih banyak pada bagian atas untuk menyeimbangkan proporsi wajah. Hindari potongan rambut yang terlalu pendek di atas dan samping, karena bisa menonjolkan bagian dahi yang lebar.\n \n Rekomendasi Gaya Rambut:\n- Quiff\n- Undercut\n- Two Block\n- Faux Hawk\n- Caesar Cut\n- Pompadour\n- Side Part"

            "Round" -> "Untuk wajah bulat, pilih gaya rambut dengan tekstur pada bagian atas dan sisi yang lebih pendek untuk memberikan ilusi wajah yang lebih tirus. Hindari potongan rambut yang terlalu pendek di sisi karena bisa mempertegas bentuk bulat wajahmu.\n \n Rekomendasi Gaya Rambut:\n- Pompadour\n- Messy Fringe\n- French Crop\n- Quiff\n- Slick Back\n- Taper Fade\n- Comma Hair"

            "Square" -> "Bentuk wajahmu adalah tipe Square. Wajah dengan garis rahang yang lebih tegas dan maskulin, potongan rambut pendek sangat cocok untuk menciptakan tampilan yang lebih sleek dan stylish. Hindari gaya rambut yang terlalu kaku atau terlalu bersudut agar garis wajah tidak terlihat lebih keras.\n \n Rekomendasi Gaya Rambut:\n- Buzz Cut\n- Crew Cut\n- Quiff\n- Textured Crop\n- Two Block\n- Spike\n- Comma Hair"

            "Oval" -> "Bentuk wajahmu adalah tipe Oval, tipe ini memiliki ciri wajah yang seimbang, dengan panjang wajah lebih besar daripada lebarnya. Dahi sedikit lebih lebar daripada dagu, dan garis rahang melengkung lembut. Wajah oval sering dianggap paling proporsional sehingga cocok dengan berbagai gaya rambut.\n \n Rekomendasi Gaya Rambut:\n- Side Swept Bangs\n- Undercut\n- Fringe\n- Textured Crop\n- Quiff\n- Pompadour\n- Comma Hair"

            else -> "Tidak ada deskripsi untuk bentuk wajah ini."
        }
    }
}
