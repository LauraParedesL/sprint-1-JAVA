const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
          type : "",
          color : "",
          isMenuOpen: false
        }
    },
    created(){
       
    },

    methods : {
       createCard(){
        axios.post("/api/clients/current/cards?cardType=" + this.type + "&color=" + this.color )
        .then(response =>
            console.log(response),
            window.location.href = "/web/html/cards.html")
        .catch(error => 
            console.log(error))
       },
       logout(){
        axios.post("/api/logout")
        .then(response => { 
            console.log(response)
            window.location.href = "/web/html/index.html"
        })
        
    },
    ontoggleMenu(){
        this.isMenuOpen = !this.isMenuOpen
    }


    }

})

app.mount("#app")