const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            modal: false,
            modal2: false,
            name: "",
            lastName : "",
            email : "",
            password : ""  
        }
    },
    

    methods : {
        mostrarModal(){
            this.modal= true
        },
        cerrarModal(){
            this.modal= false
        },

        mostrarModal2(){
            this.modal2= true
        },
        cerrarModal2(){
            this.modal2= false
        },

        login(){
            axios.post("/api/login?email=" +this.email+ "&password="+ this.password)
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/accounts.html"
            })
            .catch(e => console.log(e))
        },

        signUp(){
            axios.post("/api/clients?name=" + this.name + "&lastName=" + this.lastName + "&email=" + this.email
                         + "&password=" + this.password)
            .then(response => {
                console.log(response)
                this.clearData()
            } )
            .catch(e => console.log(e))
        },

        clearData(){
            this.email = ""
            this.password = ""
            this.name = ""
            this.lastName = ""
        }
    

        
    }

})

app.mount("#app")