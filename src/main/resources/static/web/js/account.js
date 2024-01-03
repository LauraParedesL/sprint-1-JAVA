const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            transactions: [],
            account: {}
        }
    },
    created(){
        this.id=new URLSearchParams(window.location.search).get("id")
       this.loadData()
       
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current")
            .then(response => { 
                this.data = response.data
                this.accounts = this.data.accounts
                this.account = this.accounts.find(account => account.id == this.id)
                this.transactions = this.account.transactions
                console.log(this.data)
                console.log(this.accounts)
                console.log(this.transactions)
            })
            .catch(e => console.log(e))
        },
    }

})

app.mount("#app")