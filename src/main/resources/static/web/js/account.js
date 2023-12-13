const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            transactions: []
        }
    },
    created(){
        this.id=new URLSearchParams(window.location.search).get("id")
       this.loadData()
       
    },

    methods : {
        loadData(){
            axios.get("/api/accounts/" +this.id)
            .then(response => { 
                this.data = response.data
                this.accounts = this.data.accounts
                this.transactions = this.data.transactions
                console.log(this.data)
                console.log(this.accounts)
                console.log(this.transactions)
            })
            .catch(e => console.log(e))
        },
    }

})

app.mount("#app")