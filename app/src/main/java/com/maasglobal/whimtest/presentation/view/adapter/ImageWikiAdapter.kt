package com.maasglobal.whimtest.presentation.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maasglobal.data.entities.ImageWiki
import com.maasglobal.whimtest.R
import com.maasglobal.whimtest.databinding.ItemDetailBinding

class ImageWikiAdapter(private var imageWikiList: MutableList<ImageWiki>, var context: Context) : RecyclerView.Adapter<ImageWikiAdapter.ImageWikiHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageWikiHolder {

        val itemBinding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageWikiHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageWikiHolder, position: Int) {
        holder.bind(imageWikiList[position], position)
    }

    override fun getItemCount(): Int {
        return imageWikiList.size
    }

    fun getItemAtPosition(pos: Int): ImageWiki {
        return imageWikiList[pos]
    }

    fun setImageWikiList(list: List<ImageWiki>?) {
        this.imageWikiList.clear()
        this.imageWikiList.addAll(list!!)
        notifyDataSetChanged()
    }



    inner class ImageWikiHolder(private val itemBinding: ItemDetailBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(imageWiki: ImageWiki, position: Int) = with(itemBinding) {

            itemBinding.tvWikipedia.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("url", imageWiki.formatToGetLink())
                itemBinding.root.findNavController().navigate(R.id.action_locationDetailFragment_to_wikiWebFragment, bundle)
            }

            Glide.with(itemBinding.root)
                .load(imageWiki.formatToGetImage())
                .placeholder(R.drawable.ic_wikipedia)
                .error(R.drawable.ic_wikipedia)
                .into(itemBinding.imageView)


            itemBinding.root.setOnClickListener {

            }

        }

    }
}