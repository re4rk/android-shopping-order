package woowacourse.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val binding = ItemProductBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding)
        }
    }
}
