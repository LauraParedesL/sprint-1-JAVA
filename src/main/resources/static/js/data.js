const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            clients: [],
            name: "",
            lastName: "",
            email: ""
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios("http://localhost:8080/clients")
            .then(response => { this.data = response
                this.clients = response.data._embedded.clients
            })
            .catch(e => console.log(e))
        },
    

        addClient(){
            axios.post("http://localhost:8080/clients",{
                "name": this.name,
                "lastName": this.lastName,
                "email": this.email
            })
            .then(response => {this.loadData()})
            .catch(e => console.log(e))
        }
    }

})

app.mount("#app")