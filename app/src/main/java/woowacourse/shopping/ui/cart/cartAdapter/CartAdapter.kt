package woowacourse.shopping.ui.cart.cartAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType.Cart
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType.Navigation
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartProductViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.CartViewHolder
import woowacourse.shopping.ui.cart.cartAdapter.viewHolder.NavigationViewHolder

class CartAdapter(
    private val cartListener: CartListener
) : ListAdapter<CartItemType, CartViewHolder>(CartDiffCallback()) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return when (viewType) {
            CartItemType.TYPE_ITEM -> CartProductViewHolder.from(parent, cartListener)
            CartItemType.TYPE_FOOTER -> NavigationViewHolder.from(parent, cartListener)
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        when (holder) {
            is CartProductViewHolder -> holder.bind(getItem(position) as Cart)
            is NavigationViewHolder -> holder.bind(getItem(position) as Navigation)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    fun submitList(cartProducts: List<CartProductUIModel>, pageUIModel: PageUIModel) {
        submitList(cartProducts.map { Cart(it) } + Navigation(pageUIModel))
    }

    companion object {
        private class CartDiffCallback : DiffUtil.ItemCallback<CartItemType>() {
            override fun areItemsTheSame(oldItem: CartItemType, newItem: CartItemType): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: CartItemType, newItem: CartItemType): Boolean {
                return oldItem == newItem
            }
        }
    }
}
