package io.realm.todo.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.realm.todo.R
import kotlinx.android.synthetic.main.list_item_transactions_item.view.*

internal class TransactionsRecyclerAdapter(data: OrderedRealmCollection<Transaction>) : RealmRecyclerViewAdapter<Transaction, TransactionsRecyclerAdapter.MyViewHolder>(data, true) {

    private val uiMapper = UiTransactionMapper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_transactions_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transaction = getItem(position)
        if (transaction != null) {
            val uiTransaction = uiMapper.toUiTransaction(transaction)
            updateTransactionItem(holder, uiTransaction)
        }
    }

    private fun updateTransactionItem(holder: MyViewHolder, transaction: UiTransaction) {
        holder.time.text = transaction.dateTime
        holder.card_scheme_img.setImageResource(transaction.cardSchemeImg)
        holder.card_type.text = transaction.cardType
        holder.transaction_amount.text = transaction.amount
    }

    @Suppress("PropertyName")
    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionItemContainer: ViewGroup = itemView.transaction_item_container
        val time: TextView = itemView.time
        val card_scheme_img: ImageView = itemView.card_scheme_img
        val card_type: TextView = itemView.card_type
        val masked_card_number: TextView = itemView.masked_card_number
        val transaction_amount: TextView = itemView.transaction_amount
    }
}
