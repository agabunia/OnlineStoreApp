package com.example.final_project.presentation.adapter.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.CardLayoutBinding
import com.example.final_project.presentation.extention.formatCardNumber
import com.example.final_project.presentation.extention.loadImage
import com.example.final_project.presentation.extention.loadImageId
import com.example.final_project.presentation.model.wallet.Card

class CardViewPagerAdapter :
    ListAdapter<Card, CardViewPagerAdapter.CardViewHolder>(CardDiffUtil()) {

    class CardDiffUtil : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return CardViewHolder(CardLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
    }

    inner class CardViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var card: Card
        fun bind() {
            card = currentList[adapterPosition]

            with(binding) {
                tvCardNumber.text = card.cardNumber.formatCardNumber()
                tvCardDate.text = card.date
                ivCardType.loadImageId(getCardTypeImageResource(cardType = card.cardType))
            }
        }

        private fun getCardTypeImageResource(cardType: Card.CardType): Int {
            return when (cardType) {
                Card.CardType.VISA -> R.drawable.ic_card_visa
                Card.CardType.MASTER_CARD -> R.drawable.ic_card_mc
                Card.CardType.AMERICAN_EXPRESS -> R.drawable.ic_card_amex
                else -> R.drawable.ic_card_other
            }
        }
    }
}