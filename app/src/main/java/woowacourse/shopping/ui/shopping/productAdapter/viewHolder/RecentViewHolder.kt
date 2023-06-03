package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductRecentBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType.RecentProducts
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductItem
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductsAdapter

class RecentViewHolder(
    binding: ItemProductRecentBinding,
    listener: ProductsListener
) : ShoppingViewHolder(binding.root) {
    private val adapter = RecentProductsAdapter(listener::onClickItem)

    init {
        binding.rvProducts.adapter = adapter
    }
    fun bind(recentProducts: RecentProducts) {
        adapter.submitList(recentProducts.product.map(::RecentProductItem))
    }

    companion object {
        fun from(parent: ViewGroup, listener: ProductsListener): RecentViewHolder {
            val binding = ItemProductRecentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentViewHolder(binding, listener)
        }
    }
}
