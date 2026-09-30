-- ==========================================================
-- SPEED HUB X - STANDALONE AUTO ROLL TESTER
-- ==========================================================

local Players = game:GetService("Players")
local LocalPlayer = Players.LocalPlayer
local PlayerGui = LocalPlayer:WaitForChild("PlayerGui")
local ReplicatedStorage = game:GetService("ReplicatedStorage")

local NetFolder = ReplicatedStorage:WaitForChild("Modules"):WaitForChild("Util"):WaitForChild("Net")
local BuyRandomFishRemote = NetFolder:WaitForChild("BuyRandomFish")
local AnnounceRemote = NetFolder:WaitForChild("Announce")

if PlayerGui:FindFirstChild("AutoRollTesterUI") then
    PlayerGui.AutoRollTesterUI:Destroy()
end

-- 1. SCREEN GUI UTAMA
local ScreenGui = Instance.new("ScreenGui")
ScreenGui.Name = "AutoRollTesterUI"
ScreenGui.ResetOnSpawn = false
ScreenGui.Parent = PlayerGui

local MainFrame = Instance.new("Frame")
MainFrame.Size = UDim2.new(0, 260, 0, 160)
MainFrame.Position = UDim2.new(0.5, -130, 0.4, -80)
MainFrame.BackgroundColor3 = Color3.fromRGB(18, 18, 24)
MainFrame.Active = true
MainFrame.Draggable = true
MainFrame.Parent = ScreenGui

local Corner = Instance.new("UICorner")
Corner.CornerRadius = UDim.new(0, 8)
Corner.Parent = MainFrame

local Stroke = Instance.new("UIStroke")
Stroke.Color = Color3.fromRGB(255, 30, 60)
Stroke.Thickness = 2
Stroke.Parent = MainFrame

local Title = Instance.new("TextLabel")
Title.Size = UDim2.new(1, -30, 0, 28)
Title.Position = UDim2.new(0, 10, 0, 0)
Title.BackgroundTransparency = 1
Title.Text = "Auto Roll Tester"
Title.TextColor3 = Color3.fromRGB(255, 255, 255)
Title.Font = Enum.Font.SourceSansBold
Title.TextSize = 12
Title.TextXAlignment = Enum.TextXAlignment.Left
Title.Parent = MainFrame

local CloseBtn = Instance.new("TextButton")
CloseBtn.Size = UDim2.new(0, 20, 0, 20)
CloseBtn.Position = UDim2.new(1, -24, 0, 4)
CloseBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
CloseBtn.Text = "X"
CloseBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
CloseBtn.Font = Enum.Font.SourceSansBold
CloseBtn.TextSize = 10
CloseBtn.Parent = MainFrame

local CloseCorner = Instance.new("UICorner")
CloseCorner.CornerRadius = UDim.new(0, 4)
CloseCorner.Parent = CloseBtn

CloseBtn.MouseButton1Click:Connect(function()
    ScreenGui:Destroy()
end)

local StatusLabel = Instance.new("TextLabel")
StatusLabel.Size = UDim2.new(1, -20, 0, 40)
StatusLabel.Position = UDim2.new(0, 10, 0, 32)
StatusLabel.BackgroundTransparency = 1
StatusLabel.Text = "Status: Idle\nHasil Roll Terakhir: -"
StatusLabel.TextColor3 = Color3.fromRGB(200, 200, 210)
StatusLabel.Font = Enum.Font.SourceSansSemibold
StatusLabel.TextSize = 10
StatusLabel.TextXAlignment = Enum.TextXAlignment.Left
StatusLabel.Parent = MainFrame

local ToggleBtn = Instance.new("TextButton")
ToggleBtn.Size = UDim2.new(1, -20, 0, 32)
ToggleBtn.Position = UDim2.new(0, 10, 0, 80)
ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
ToggleBtn.Text = "START AUTO ROLL"
ToggleBtn.TextColor3 = Color3.fromRGB(255, 255, 255)
ToggleBtn.Font = Enum.Font.SourceSansBold
ToggleBtn.TextSize = 11
ToggleBtn.Parent = MainFrame

local BtnCorner = Instance.new("UICorner")
BtnCorner.CornerRadius = UDim.new(0, 6)
BtnCorner.Parent = ToggleBtn

-- 2. LOGIKA MONITORING ROLL ANNOUNCE
local isRolling = false
local lastRolledFisherman = ""

AnnounceRemote.OnClientEvent:Connect(function(...)
    local args = {...}
    -- Mengecek jika event ini adalah respon dari roll player sendiri
    if #args >= 3 and tostring(args[1]) == LocalPlayer.Name and tostring(args[2]) == "rolled" then
        lastRolledFisherman = tostring(args[3])
        StatusLabel.Text = "Status: Rolling...\nDapat: " .. lastRolledFisherman
    end
end)

-- 3. EKSEKUSI AUTO ROLL
ToggleBtn.MouseButton1Click:Connect(function()
    isRolling = not isRolling
    if isRolling then
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(255, 30, 60)
        ToggleBtn.Text = "STOP AUTO ROLL"
        
        task.spawn(function()
            while isRolling do
                -- Panggil Remote Roll
                if BuyRandomFishRemote:IsA("RemoteEvent") then
                    BuyRandomFishRemote:FireServer()
                elseif BuyRandomFishRemote:IsA("RemoteFunction") then
                    BuyRandomFishRemote:InvokeServer()
                end
                
                task.wait(0.2) -- Jeda aman gacha
            end
        end)
    else
        ToggleBtn.BackgroundColor3 = Color3.fromRGB(60, 60, 75)
        ToggleBtn.Text = "START AUTO ROLL"
        StatusLabel.Text = "Status: Stopped\nHasil Roll Terakhir: " .. (lastRolledFisherman ~= "" and lastRolledFisherman or "-")
    end
end)
