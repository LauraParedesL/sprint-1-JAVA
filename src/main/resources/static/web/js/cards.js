const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            loans: [],
            cards: [],
            debit: [],
            credit: []
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
                this.cards = this.data.cards
                this.debit=this.cards.filter(card => card.cardType == 'DEBIT')
                this.credit=this.cards.filter(card => card.cardType == 'CREDIT')
                console.log(this.cards)
                console.log(this.debit)
                console.log(this.credit)
            })
            .catch(e => console.log(e))
        },

    }

})

app.mount("#app")