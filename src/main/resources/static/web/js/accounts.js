const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            loans: [],
            
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current")
            .then(response => { 
                this.data = response.data
                this.accounts = this.data.accounts
                this.loans = response.data.loans
                console.log(this.data)
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

        createAccount(){
            axios.post("/api/clients/current/accounts")
            .then(response => { 
                console.log(response)
            }).catch(e => console.log(e))
        }
    }

})

app.mount("#app")