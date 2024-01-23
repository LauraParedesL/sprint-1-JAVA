const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:0,
            loans: [],
            cards: [],
            debit: [],
            credit: [],
            input: false,
            selectButton: true,
            card:true,
            isMenuOpen: true
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
        logout(){
            axios.post("/api/logout")
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/index.html"
            })
            
        },
        showInput(){
            this.input=true
            this.selectButton=false
        },
        deleteCard(cardId){
            axios.patch("/api/cards/" + cardId)
            .then(response => { 
                this.loadData()
                console.log(response)
            }).catch(e => console.log(e))
        },
        expiredCard(toDate){
            const currentDate = new Date()
            const expirationDate = new Date(toDate)
            return expirationDate < currentDate
        },
        ontoggleMenu(){
            this.isMenuOpen = !this.isMenuOpen
        }

    }

})

app.mount("#app")