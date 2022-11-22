package com.example.InventoryService;

import com.example.InventoryService.model.Inventory;
import com.example.InventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class InventoryController {
    static final int budgetPort = 8000;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @GetMapping("/show")
    public Inventory showHistoryOne(@RequestParam int id) throws SQLException, ClassNotFoundException {
        var service = new InventoryService();

        return service.selectById(id);
    }

    @GetMapping("/showAll")
    public Iterable<Inventory> showHistory() throws SQLException {
        var service = new InventoryService();

        return service.showAll();
    }

    @GetMapping("/buy")
    public void buy(@RequestParam String name, int amount, double price) throws SQLException {
        var service = new InventoryService();
        service.Buy(new Inventory(name, amount));

        var rest = restTemplateBuilder.build();

        var query = "http://localhost:"+budgetPort+"/budget/income?moneyAmount="+(int)(price*amount)+"&description="+"куплено"+name;

        rest.getForObject(query, String.class);
    }

    @GetMapping("/decrease")
    public ResponseEntity buy(@RequestParam int id, int amount) throws SQLException, ClassNotFoundException {
        var service = new InventoryService();
        if(!service.Decrease(id, amount))
        {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }
}
