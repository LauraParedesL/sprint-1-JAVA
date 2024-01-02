const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            transactions: []
           
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/transactions")
            .then(response => { 
                this.data = response.data
                this.transactions = this.data.transactions
                
            })
            .catch(e => console.log(e))
        },

    }

})

app.mount("#app")