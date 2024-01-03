const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            accounts: [],
            transactions: [],
            originAccount: "",
            destinationAccount: "",
            description: "",
            amount: 0
           
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current/accounts")
            .then(response => { 
                this.accounts = response.data
                console.log(this.accounts)
                
            })
            .catch(e => console.log(e))
        },
        logout(){
            axios.post("/api/logout")
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/index.html"
            })
            
        },
        createTransaction(){
            axios.post("/api/transactions?amount=" + this.amount + 
            "&description=" + this.description + 
            "&originAccount=" + this.originAccount + 
            "&destinationAccount=" + this.destinationAccount)
            
        }
       

    }

})

app.mount("#app")