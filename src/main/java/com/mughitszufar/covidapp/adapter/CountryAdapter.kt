package com.mughitszufar.covidapp.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mughitszufar.covidapp.R
import com.mughitszufar.covidapp.model.Negara
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_country.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter (

    private var negara: ArrayList<Negara>,
    private val clickListener: (Negara) -> Unit
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(), Filterable {

    var countryFilterlist = ArrayList<Negara>()
    init {
        countryFilterlist = negara
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_country,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryFilterlist.size
    }

    override fun onBindViewHolder(holder: CountryAdapter.ViewHolder, position: Int) {
        holder.bind(countryFilterlist[position],clickListener)
    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(negara: Negara, clickListener: (Negara) -> Unit){
            val country : TextView = itemView.countryName
            val cTotalCase: TextView = itemView.country_total_case
            val cTotalRecoreved : TextView = itemView.country_total_recovered
            val cTotalDeaths : TextView =  itemView.country_total_deaths
            val flag : CircleImageView = itemView.img_flag_circle


            val formatter: NumberFormat = DecimalFormat("#,###")

            country.countryName.text = negara.Country
            cTotalCase.country_total_case.text = formatter.format(negara.TotalConfirmed?.toDouble())
            cTotalRecoreved.country_total_recovered.text = formatter.format(negara.TotalRecovered?.toDouble())
            cTotalDeaths.country_total_deaths.text = formatter.format(negara.TotalDeaths?.toDouble())
            Glide.with(itemView)
                .load("https://www.countryflags.io/" + negara.CountryCode + "/flat/16.png")
                .into(flag)

            country.setOnClickListener{ clickListener(negara) }
            cTotalCase.setOnClickListener { clickListener(negara) }
            cTotalRecoreved.setOnClickListener { clickListener(negara) }
            cTotalDeaths.setOnClickListener { clickListener(negara) }
            flag.setOnClickListener { clickListener(negara) }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterlist = if (charSearch.isEmpty()){
                    negara
                }else{
                    val resultList = ArrayList<Negara>()
                    for (row in negara){
                        val search = row.Country?.toLowerCase(Locale.ROOT) ?:""
                        if (search.contains(charSearch.toLowerCase(Locale.ROOT))){
                          resultList.add(row)
                        }

                    }

                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = countryFilterlist
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterlist = results?.values as ArrayList<Negara>
                notifyDataSetChanged()

            }


        }

        }
    }

