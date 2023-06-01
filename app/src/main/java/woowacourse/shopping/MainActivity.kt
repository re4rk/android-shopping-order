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

        RetrofitRepository.getProducts { result ->
            result.onSuccess {
                adapter.replaceProducts(it)
                binding.tvProductCount.text = it.size.toString()
            }.onFailure {
                println(it)
            }
        }
    }
}
