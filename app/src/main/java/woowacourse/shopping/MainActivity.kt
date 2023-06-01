package woowacourse.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter = ProductAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvProducts.adapter = adapter

        Thread {
            val products = RetrofitRepository.getProducts()
            runOnUiThread {
                adapter.replaceProducts(products)
                binding.tvProductCount.text = products.size.toString()
            }
        }.start()
    }
}
