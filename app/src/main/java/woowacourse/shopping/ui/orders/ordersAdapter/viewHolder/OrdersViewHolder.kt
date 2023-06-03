package woowacourse.shopping.ui.orders.ordersAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemOrdersBinding
import woowacourse.shopping.model.OrderUIModel
import woowacourse.shopping.ui.orders.orderItemAdapter.OrderItemAdapter
import woowacourse.shopping.ui.orders.ordersAdapter.OrdersListener

class OrdersViewHolder(
    private val binding: ItemOrdersBinding,
    listener: OrdersListener
) : RecyclerView.ViewHolder(binding.root) {
    private val adapter = OrderItemAdapter(listener::onItemClick)

    init {
        binding.rvProducts.adapter = adapter
        binding.onOrderDetailClick = listener::onOrderDetailClick
    }

    fun bind(order: OrderUIModel) {
        adapter.submitList(order.orderItems)
        binding.order = order
    }

    companion object {
        fun from(parent: ViewGroup, listener: OrdersListener): OrdersViewHolder {
            val binding = ItemOrdersBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return OrdersViewHolder(binding, listener)
        }
    }
}
