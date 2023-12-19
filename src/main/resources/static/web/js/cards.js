const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            loans: [],
            cards: []
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
                this.cards = this.data.cards
            })
            .catch(e => console.log(e))
        }
    }

})

app.mount("#app")