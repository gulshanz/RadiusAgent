package taksande.gulshan.radiusagent.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import taksande.gulshan.radiusagent.R
import taksande.gulshan.radiusagent.data.model.Exclusion
import taksande.gulshan.radiusagent.data.model.Facility
import taksande.gulshan.radiusagent.data.model.Option
import taksande.gulshan.radiusagent.databinding.FacilityItemBinding
import taksande.gulshan.radiusagent.databinding.ItemOptionsBinding

class FacilityAdapter : RecyclerView.Adapter<FacilityAdapter.ViewHolder>() {

    private var facilityList: List<Facility> = emptyList()
    private var exclusions: List<List<Exclusion>> = emptyList()
    private val exclusionsMap: HashMap<String, String> = HashMap()
    private val checkedItemsList: ArrayList<String> = ArrayList()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Facility>) {
        facilityList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: FacilityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Facility) {

            val innerAdapter = OptionsAdapter(this@FacilityAdapter, data.facility_id)
            binding.textName.text = data.name
            binding.executePendingBindings()

            // setting up inner recyclerview
            val innerRecyclerView = binding.rvOptions
            innerRecyclerView.layoutManager = LinearLayoutManager(innerRecyclerView.context)
            innerRecyclerView.adapter = innerAdapter
            innerAdapter.setData(data.options)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FacilityItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.facility_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return facilityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = facilityList[position]
        holder.bind(data)
    }

    fun setExclusion(exclusions: List<List<Exclusion>>) {
        this.exclusions = exclusions
        for (singleExclusion in exclusions) {
            val key = "${singleExclusion[0].facility_id}_${singleExclusion[0].options_id}"
            val value = "${singleExclusion[1].facility_id}_${singleExclusion[1].options_id}"
            exclusionsMap[key] = value
        }
    }

    inner class OptionsAdapter(
        private val outerAdapter: FacilityAdapter, private val facilityId: String
    ) : RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

        private var dataList: List<Option> = emptyList()


        inner class ViewHolder(private val binding: ItemOptionsBinding) :
            RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("DiscouragedApi")
            fun bind(option: Option) {
                binding.textOption.text = option.name
                var iconName = option.icon
                if (iconName.contains("-")) iconName = iconName.replace("-", "_")
                binding.checkbox.isActivated = option.isChecked

                // for fetching icon ids dynamically
                val resourceId = binding.root.context.resources.getIdentifier(
                    iconName, "drawable", binding.root.context.packageName
                )

                binding.imageIcon.setImageResource(resourceId)
                binding.executePendingBindings()

                binding.checkbox.setOnCheckedChangeListener { bt, checkStatus ->

                    val checkHash = "${facilityId}_${option.id}"

                    Log.d("testLog", "bind: CheckedItemList :$checkedItemsList")
                    Log.d("testLog", "bind: checkMap :$exclusionsMap")
                    if (!exclusionsMap.containsKey(checkHash) && !exclusionsMap.containsValue(
                            checkHash
                        )
                    ) {
                        // no need to do anything
                    } else if ((exclusionsMap.containsKey(checkHash)) && !checkedItemsList.contains(
                            exclusionsMap[checkHash]
                        )

                    ) {
                        // no need to do anything
                        Log.d("test", "bind: ")
                    } else if (exclusionsMap.containsValue(checkHash)) {
                        val keyForValue = exclusionsMap.entries.find { it.value == checkHash }?.key
                        if (checkedItemsList.contains(keyForValue)||checkedItemsList.contains(checkHash)) {
                            bt.isChecked = false
                        }
                    } else {
                        bt.isChecked = false
                    }
                    addItemToCheckList(checkedItemsList, checkHash, bt.isChecked)


                }
            }

            private fun addItemToCheckList(
                checkedItemsList: java.util.ArrayList<String>,
                checkHash: String,
                checkStatus: Boolean
            ) {
                if (checkedItemsList.contains(checkHash) && !checkStatus) {
                    checkedItemsList.remove(checkHash)
                } else if (!checkedItemsList.contains(checkHash) && checkStatus) {
                    checkedItemsList.add(checkHash)
                }
                Log.d("testLog", "addItemToCheckList: After adding : $checkedItemsList")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ItemOptionsBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_options, parent, false)

            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(dataList[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setData(options: List<Option>) {
            dataList = options
            notifyDataSetChanged()
        }
    }
}