const express = require("express");
const app = express();
// This is a public sample test API key.
// Don’t submit any personally identifiable information in requests made with this key.
// Sign in to see your own test API key embedded in code samples.
const stripe = require("stripe")('sk_test_51Qg0pMHJsLoOY2AhwLQMtyZs1F3Xt4F5aATqj3nWSN2ZqtD73FLpCh9h9MF8JfzHrBTGNlqEEwCV3FhOKWvpXnrH009uOt5spo');

app.use(express.static("public"));
app.use(express.json());

const calculateOrderAmount = () => {
  // Charge a fixed amount of $5
  return 500;
};


app.post("/create-payment-intent", async (req, res) => {
  const { items } = req.body;

  // Create a PaymentIntent with the order amount and currency
  const paymentIntent = await stripe.paymentIntents.create({
    amount: calculateOrderAmount(items),
    currency: "usd",
    // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
    automatic_payment_methods: {
      enabled: true,
    },
  });

  res.send({
    clientSecret: paymentIntent.client_secret,
  });
});

app.get("/greet", async (req, res) => {
  res.send("Hello, it's working!");
});

app.listen(process.env.PORT || 4242, () => console.log("Node server listening!"));