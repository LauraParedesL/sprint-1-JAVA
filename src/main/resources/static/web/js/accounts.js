const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            loans: []
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/" +this.id)
            .then(response => { 
                this.data = response.data
                this.accounts = this.data.accounts
                this.loans = response.data.loans
                console.log(this.data)
                console.log(this.accounts)
            })
            .catch(e => console.log(e))
        },
    
        /*
        addClient(){
            axios.post("http://localhost:8080/api/clients",{
                "name": this.name,
                "lastName": this.lastName,
                "email": this.email
            })
            .then(response => {this.loadData()})
            .catch(e => console.log(e))
        }*/
    }

})

app.mount("#app")